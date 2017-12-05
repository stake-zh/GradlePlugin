package com.stake.dellog

import org.gradle.api.Plugin
import org.gradle.api.Project


class DelLogPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {


        project.extensions.create('dellogExtension', DelLogExtension);
        def android = project.extensions.android

        project.afterEvaluate {
            //在gradle 构建完之后执行
            project.logger.error("dellogExtension1 : " + project.dellogExtension.sourceDir);

            android.applicationVariants.all { variant ->
                println("$variant.dirName") //release
                println("$variant.name") // release
                println("$variant.name".capitalize()) // Release
                println("$variant.assemble") //task ':app:assembleRelease'
                println("$variant.outputs") //[ApkVariantOutputImpl_Decorated{apkData=Main{type=MAIN, fullName=debug, filters=[]}}]
                variant.outputs.each{ outputs ->
                    println("$outputs") //ApkVariantOutputImpl_Decorated{apkData=Main{type=MAIN, fullName=release, filters=[]}}
                    println("$outputs.outputFile") // /xxx/AndroidDemo/app/build/outputs/apk/release/app-release-unsigned.apk
                }
                def taskName = variant.name
            }

            def rootDir = project.projectDir.toString().plus(project.dellogExtension.sourceDir);

            project.logger.error(rootDir);

            DelLogUtil.delLog(new File(rootDir));
        }

        project.task('dellog', {
            project.logger.error("dellogExtension2 : " + project.dellogExtension.sourceDir);

            def rootDir = project.projectDir.toString().plus(project.dellogExtension.sourceDir);

            project.logger.error(rootDir);

            DelLogUtil.delLog(new File(rootDir));

        })

    }
}
