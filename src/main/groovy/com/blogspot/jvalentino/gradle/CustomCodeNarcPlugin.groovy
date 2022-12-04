package com.blogspot.jvalentino.gradle

import org.apache.commons.io.IOUtils
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * <p>A basic gradle plugin.</p>
 * @author jvalentino2
 */
@SuppressWarnings(['Println', 'DuplicateStringLiteral', 'FileCreateTempFile'])
class CustomCodeNarcPlugin implements Plugin<Project> {

    File mainFile = getTempFileFromClasspath('/CodeNarcMain.txt')
    File testFile = getTempFileFromClasspath('/CodeNarcTest.txt')

    void apply(Project project) {
        project.configure(project) {
            apply plugin:'codenarc'

            afterEvaluate {
                codenarc { toolVersion = '1.1' }

                codenarcMain {
                    configFile = mainFile
                    reports {
                        html.enabled = true
                        xml.enabled = true
                    }
                }

                codenarcTest { configFile = testFile }

                project.task('echoCodenarc') {
                    println "- Codenarc Main: ${mainFile.absolutePath}"
                    println "- Codenarc Test: ${testFile.absolutePath}"
                }
                project.tasks.codenarcMain.finalizedBy 'echoCodenarc'
            }
        }
    }

    InputStream getResourceAsStream(String classpath) {
        this.getClass().getResourceAsStream(classpath)
    }

    File getTempFileFromClasspath(String classpath) {
        File file = File.createTempFile('codenarc', '.groovy')
        FileOutputStream out = new FileOutputStream(file)
        InputStream input = this.getResourceAsStream(classpath)
        IOUtils.copy(input, out)
        file.deleteOnExit()
        file
    }
}
