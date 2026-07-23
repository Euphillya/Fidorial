plugins {
    id("com.diffplug.spotless")
}

spotless {
    ratchetFrom("origin/master")

    format("misc") {
        target(
            "*.gradle.kts",
            "*.md",
            "*.properties",
            "*.json",
            "*.toml",
            "*.xml",
            "*.yml",
            "*.yaml",
        )
        targetExclude(
            ".gradle/**",
            ".idea/**",
            "**/build/**",
            "**/run/**",
        )
        trimTrailingWhitespace()
        endWithNewline()
    }

    java {
        target("**/*.java")

        //palantirJavaFormat("2.96.0")
        forbidWildcardImports()
        formatAnnotations()
        removeUnusedImports()
        //custom("Wrap declaration parameters", WrapDeclarationParameters())
        replaceRegex("Expand empty code blocks", """(?m)^([ \t]*)(.+) \{\}$""", "$1$2 {\n$1}")
        replaceRegex(
            "Separate module requires static",
            """(?m)^(    requires (?!static )[^\n]+;\n)(    requires static )""",
            "$1\n$2",
        )

        importOrder("", "javax|java", "\\#")
    }
}
