
var app = angular.module('app', []);

app.controller('AppCtrl', function($scope,$http) {
	var app = this;
	$scope.member = 0;
	$http.get("/resources/members/loggedIn")
	.then( function(data) {
		$scope.member = data.data;
	});
}).directive('navBar', function() {
	return {
		templateUrl:"navbar.html"
	};
});