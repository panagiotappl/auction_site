var profileController = router.controller('profileController', function($scope, $state, $http,$cookies, $route, AuthenticationService){
	$scope.user = {};
	$scope.hasBuyout = true;
	$scope.signedIn = {};
	$scope.items = {};
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
			
			console.log($scope.user);

		}, function errorCallback(response){
			alert("error?");
			
			
		});
	
		$http.get('api/auctionitem/user/'+ $scope.user.userId).then(function successCallback(response){
			$scope.hasAuctions = false;
			if(response.data.length != 0)
				$scope.hasAuctions = true;
			$scope.items = {};
			$scope.items = response.data;
			var i;
			for (i = 0; i < $scope.items.length; i++){
				if($scope.items[i].buyout != null)
					$scope.items[i].hasBuyout = true;
				else
					$scope.items[i].hasBuyout = false;
			}
			console.log($scope.items.length);
			console.log($scope.items);
		}, function errorCallback(response){
			alert("errorauction");
		});
		
	}else
		$scope.signedIn = false;
	
	
	$scope.redirectLogin = function(){
		 $state.go("login");
		
	};	
	$scope.redirectRegister = function(){
		$state.go("register");
	};	
	
	$scope.logout = function(){
		$cookies.remove('userId');
		$cookies.remove('authToken');
		$cookies.put('signedIn', 'no');
		$state.go("welcome"); 
	};
	
	$scope.show = function(field){
	
		document.getElementById(field+"Form").style.display = "block";
		document.getElementById(field+"Cont").style.backgroundColor = "#edf1f4";
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
	
	$scope.newAuction = function(){
		$state.go("newAuction");
	};
	
	$scope.showItem = function(){
		console.log("test");
	}
	///////////////////////////////////////////////////////////////////////////
	/*var timer;
	//console.log($scope.items[0]);
	var compareDate = new Date();
	compareDate.setDate(compareDate.getDate() + 7); //just for this demo today + 7 days

	timer = setInterval(function() {
	  timeBetweenDates(compareDate);
	}, 1000);

	function timeBetweenDates(toDate) {
	  var dateEntered = toDate;
	  var now = new Date();
	  var difference = dateEntered.getTime() - now.getTime();

	  if (difference <= 0) {

	    // Timer done
	    clearInterval(timer);
	  
	  } else {
	    
	    var seconds = Math.floor(difference / 1000);
	    var minutes = Math.floor(seconds / 60);
	    var hours = Math.floor(minutes / 60);
	    var days = Math.floor(hours / 24);

	    hours %= 24;
	    minutes %= 60;
	    seconds %= 60;

	    $("#days").text(days);
	    $("#hours").text(hours);
	    $("#minutes").text(minutes);
	    $("#seconds").text(seconds);
	  }
	}
	<div id="timer">
  						<span id="days"></span>d
  						<span id="hours"></span>h
  						<span id="minutes"></span>m
  						<span id="seconds"></span>s
				</div>*/
	
});