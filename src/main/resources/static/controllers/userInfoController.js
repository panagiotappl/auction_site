router.controller('userInfoController', function($state, $scope, $cookies, $http, userDataService, AuthenticationService){
	$scope.user = {};
	$scope.hasBuyout = true;
	$scope.signedIn = {};
	$scope.items = {};
	
	$scope.collapseMenu = function() {
		if ($(window).width() <= 768) {
			console.log('hiding');
		    $scope.navCollapsed = true;
		}
	}
	
	
	if($cookies.get('signedIn') === 'yes'){
		$scope.user.userId = $cookies.get('userId');
		$scope.signedIn = true;
		var token = $cookies.get('authToken');
		$http.get('/api/user/'+ $scope.user.userId, {headers: {'authToken': token}}).then(function successCallback(response){
			$scope.user = angular.copy(response.data);
			
			if($scope.user.gender == "F")
				$scope.gender = "Female";
			else
				$scope.gender = "Male";
			
			if($scope.user.isAdmin == true)
				$scope.user.type = "Administrator";
			else
				$scope.user.type = "User";
			
			$scope.dateOfBirthConverted = $.datepicker.formatDate("M d, yy", new Date(response.data.dateOfBirth));
			$scope.user.dateOfBirth = new Date(response.data.dateOfBirth);
			
			$scope.registrationDateConverted = $.datepicker.formatDate("M d, yy", new Date(response.data.registrationDate));
			$scope.user.registrationDate = new Date(response.data.registrationDate);
			console.log($scope.user);

		}, function errorCallback(response){
			alert("error?");
			
			
		});
		
		$scope.show = function(field){
			console.log(field+"Form");
			document.getElementById(field+"Form").style.display = "block";
			document.getElementById(field+"But").style.display = "none";
			document.getElementById(field+"Field").style.display = "none";
		};
		
		$scope.applyChanges = function(user){
			AuthenticationService.updateUser(user).then(function (response){
				console.log(response);
				
				
			}, function(response){
				console.log(response);
			});
			$state.go($state.current, {}, {reload: true});
			
		};
	}
	
});