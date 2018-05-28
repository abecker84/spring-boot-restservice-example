// Copyright 2018, Andreas Becker (andreas AT becker DOT name)
// 
// This file is part of The Spring Boot REST Service example.
// 
// The Spring Boot REST Service example is free software: you can redistribute
// it and/or modify it under the terms of the GNU General Public License as
// published by the Free Software Foundation, either version 3 of the License,
// or (at your option) any later version.
// 
// The Spring Boot REST Service example is distributed in the hope that it will
// be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
// Public License for more details. You should have received a copy of the GNU
// General Public License along with The Spring Boot REST Service example.
// If not, see <http://www.gnu.org/licenses/>.

// Jenkinsfile for Spring-Boot REST service example

pipeline {
    agent any
    stages {
        stage('Build'){
        	steps {
        		echo 'Building Spring-Boot REST service example...'
           		checkout scm
           		sh 'mvn -B clean install'   
        	}
        	post {
        	    always {
        	    	archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        	        junit 'target/surefire-reports/*.xml'
        	    }        	    
        	}
        }
		stage('Deploy'){
			steps {
				echo 'Deploying Spring-Boot REST service example...'   
			}		    
		}
    }
}