node {
    stage('Git CheckOut', {
        println "Git CheckOut Started"
        checkout(
                [
                        $class: 'GitSCM',
                        branches: [[name: '*/develop'], [name: '*/deploy_test']],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [],
                        submoduleCfg: [],
                        userRemoteConfigs: [[url: 'https://github.com/WoowaCrew/WoowaCrew.git']]
                ]
        )
        println "Git CheckOut End"
    })

    stage('Test/Build') {
        println "Test/Build Started"

        try {
            withCredentials(
                    [
                            file(credentialsId: 'APPLICATION_CONFIG', variable: 'config'),
                            file(credentialsId: 'GITHUB', variable: 'github'),
                            file(credentialsId: 'SLACK_TEST', variable: 'slack_test'),
                            file(credentialsId: 'SLACK', variable: 'slack')
                    ]) {
                sh "cp -f \$github $JENKINS_HOME/workspace/WoowaCrew/src/main/resources/github.yml"
                sh "cp -f \$slack $JENKINS_HOME/workspace/WoowaCrew/src/main/resources/slack.yml"
                sh "cp -f \$slack_test $JENKINS_HOME/workspace/WoowaCrew/src/test/resources/slack.yml"
                sh "cp -f \$config $JENKINS_HOME/workspace/WoowaCrew/src/main/resources/application.yml"
            }
            sh "./gradlew clean build --info"
        } finally {
            junit allowEmptyResults: true, keepLongStdio: true, testResults: 'build/test-results/*.xml'
        }
        println "Test/Build End"
    }

    stage('Coverage Report', {
//        TODO: Do Something
    })

    stage('Push Dockerhub', {
        println "Push Dockerhub"

        withCredentials([usernamePassword( credentialsId: 'dockerhub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            docker.withRegistry('https://index.docker.io/v1', 'dockerhub') {
                sh "docker login -u ${USERNAME} -p ${PASSWORD}"
                def dockerImage = docker.build("oeeen/woowacrew:lts")
                dockerImage.push()
            }
        }
        println "Backend done"

        // TODO: Deploy FrontEnd

        println "Deploy End"
    })

    stage('Remove Unused docker image') {
        sh "docker rmi oeeen/woowacrew:lts"
    }
}