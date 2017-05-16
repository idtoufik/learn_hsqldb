app.controller('user', function($scope, $http){
	$http.get("/resources/members/loggedIn")
	.then( function(data) {
		$http.get("/resources/members/"+data.data.id)
		.then(function(data){
			$scope.member = data.data;
			console.log(data.data);
		});
	});
	$scope.saveModMember = function() {
		var m = {
				pseudo: $scope.member.pseudo,
				name: $scope.member.name,
				firstName: $scope.member.firstName,
				date_of_birth: $scope.member.date_of_birth,
				email: $scope.member.email,
                dateOfRegistration : $scope.member.dateOfRegistration
		}
		console.log(m)
		$http.put("/resources/members/"+$scope.member.id, m)
		.then( function(){
			console.log("avec success");
			window.location = '/services/profil.html';
			//$scope.$apply(function() { $location.path("/services/profil.html"); });
		})
	}
    
    $scope.deleteCourse = function(idCourse)
    {
        $http.delete("/resources/courses/"+idCourse).then(function(data){
            console.log("supprimé avec success")
            location.reload()
        })
    }
	
});