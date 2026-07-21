package fr.euphyllia.fidorial.gradle

import com.diffplug.spotless.FormatterFunc

class WrapDeclarationParameters : FormatterFunc, java.io.Serializable {
    override fun apply(source: String): String {
        val lines = source.lines()
        val formatted = mutableListOf<String>()
        var index = 0

        while (index < lines.size) {
            val line = lines[index]
            if (!isDeclarationStart(line)) {
                formatted.add(line)
                index++
                continue
            }

            val declaration = mutableListOf(line)
            var parenDepth = count(line, '(') - count(line, ')')
            index++

            while (index < lines.size && parenDepth > 0) {
                declaration.add(lines[index])
                parenDepth += count(lines[index], '(') - count(lines[index], ')')
                index++
            }

            if (declaration.size == 1 || !declaration.last().trimEnd().endsWith("{")) {
                formatted.addAll(declaration)
                continue
            }

            formatted.addAll(wrapDeclaration(declaration))
        }

        return formatted.joinToString("\n")
    }

    private fun isDeclarationStart(line: String): Boolean {
        val trimmed = line.trimStart()
        return !trimmed.startsWith("if ") &&
            !trimmed.startsWith("for ") &&
            !trimmed.startsWith("while ") &&
            !trimmed.startsWith("switch ") &&
            !trimmed.startsWith("catch ") &&
            !trimmed.startsWith("try ") &&
            !trimmed.startsWith("return ") &&
            line.contains("(") &&
            !line.trimEnd().endsWith(";")
    }

    private fun wrapDeclaration(declaration: List<String>): List<String> {
        val signature = declaration.joinToString(" ") { it.trim() }.replace(Regex("""\s+"""), " ")
        val openParen = signature.indexOf('(')
        val closeParen = signature.lastIndexOf(')')
        if (openParen !in 0..closeParen) {
            return declaration
        }

        val prefix = signature.substring(0, openParen + 1)
        val params = splitParameters(signature.substring(openParen + 1, closeParen))
        if (params.size < 2) {
            return declaration
        }

        val suffix = signature.substring(closeParen + 1)
        val baseIndent = declaration.first().takeWhile { it == ' ' || it == '\t' }
        val paramIndent = "$baseIndent        "

        return buildList {
            add(baseIndent + prefix)
            params.forEachIndexed { paramIndex, param ->
                val comma = if (paramIndex == params.lastIndex) "" else ","
                add(paramIndent + param + comma)
            }
            add("$baseIndent)$suffix")
        }
    }

    private fun splitParameters(params: String): List<String> {
        val result = mutableListOf<String>()
        var angleDepth = 0
        var parenDepth = 0
        var bracketDepth = 0
        var start = 0

        params.forEachIndexed { index, char ->
            when (char) {
                '<' -> angleDepth++
                '>' -> if (angleDepth > 0) angleDepth--
                '(' -> parenDepth++
                ')' -> if (parenDepth > 0) parenDepth--
                '[' -> bracketDepth++
                ']' -> if (bracketDepth > 0) bracketDepth--
                ',' -> if (angleDepth == 0 && parenDepth == 0 && bracketDepth == 0) {
                    result.add(params.substring(start, index).trim())
                    start = index + 1
                }
            }
        }

        result.add(params.substring(start).trim())
        return result.filter { it.isNotEmpty() }
    }

    private fun count(value: String, char: Char): Int = value.count { it == char }
}
