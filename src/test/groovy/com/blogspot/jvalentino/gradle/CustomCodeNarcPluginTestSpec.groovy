package com.blogspot.jvalentino.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

import spock.lang.Specification

class CustomCodeNarcPluginTestSpec extends Specification {

    Project project
    CustomCodeNarcPlugin plugin

    def setup() {
        project = ProjectBuilder.builder().build()
        plugin = new CustomCodeNarcPlugin()
    }

    void "test plugin"() {
        when:
        plugin.apply(project)

        then:
        plugin.mainFile.exists()
        plugin.testFile.exists()
    }
}
