app.controller("cours", function($scope, $http, $location) {
				idcours = $location.search().idCourse
                $scope.itIsHisCourse = false;
                if(idcours == undefined)
                    windo.location = "/"
				$scope.addLesson = function()
				{
					window.location = "/services/lessons/addLesson.html/#!/?idCours="+idcours
				}
				$http.get("/resources/courses/" + idcours).then(function(data) {
					$scope.cours = data.data;
                    $scope.deleteLesson = function(idLesson){
                        $http.delete("/resources/courses/1/lessons/"+idLesson)
                            .then(function(data){
                                console.log("ok")
                                location.reload()
                           
                        })
                    }
                    $scope.cours.lessons.sort(function(a,b){
                        return a.dateOfCreation - b.dateOfCreation;
                    })
                    
                     $http.get("/resources/members/loggedIn")
                    .then(function(result){
                        if(result.data.id == $scope.cours.member.id)
                            {
                                
                                $scope.itIsHisCourse = true;
                            }
                })
				});
               
    
               
			});