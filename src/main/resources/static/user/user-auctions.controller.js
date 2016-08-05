router.controller('userAuctionsController', function($state, $scope, $http, $cookies){
	$scope.user = {};
	$scope.signedIn = {};
	$scope.items = {};
	$scope.hasAuctions = false;
	
	if($cookies.get('signedIn') === 'yes'){
		$scope.user.userId = $cookies.get('userId');
		$scope.signedIn = true;
		var token = $cookies.get('authToken');
		$http.get('/api/user/'+ $scope.user.userId, {headers: {'authToken': token}}).then(function successCallback(response){
			$state.go('main.profile.userAuctions.active');
		}, function errorCallback(response){
			
			$cookies.remove('userId');
			$cookies.remove('authToken');
			$cookies.put('signedIn', 'no');
			$state.go('main.welcome');
		});
	}
	
	
	var getAuctionItems = function(){
		$http.get('api/auctionitem/user/'+ $scope.user.userId+"?status=ACTIVE").then(function successCallback(response){
			if(response.data.length != 0)
				$scope.hasAuctions = true;
			$scope.items = {};
			$scope.items = response.data;
			var i;
			
			console.log($scope.items.length);
			console.log($scope.items);
		}, function errorCallback(response){
			console.log(response);
		});
		$http.get('api/auctionitem/user/'+ $scope.user.userId+"?status=PENDING").then(function successCallback(response){
			if(response.data.length != 0)
				$scope.hasAuctions = true;
			$scope.itemsPending = {};
			$scope.itemsPending = response.data;
			var i;
			
			
		}, function errorCallback(response){
			console.log(response);
		});
		
	}
	$scope.newAuction = function(){
		$state.go("main.newAuction");
	};
	
	
	$scope.timeUntil = function(stDate) {

	
	 
	    var d = new Date(stDate);
	    var diff = d - new Date();
	    
	    diff = Math.abs(diff);
	  
	    var days = Math.round(diff / 8.64e7);
	    var hours = diff % 8.64e7 / 3.6e6 | 0;
	    var mins = diff % 3.6e6 / 6e4 | 0;
	    var secs = Math.round(diff % 6e4 / 1e3);
	    
	    
	    return days + 'd ' + hours + 'h ' + mins + 'm ' + secs + 's';
	  }

	  $scope._timeLeft = [];

	  var intervalID = window.setInterval(function() {
	      for (var i=0; i<$scope.items.length; i++) {
	    	  
	        $scope._timeLeft[i] = $scope.timeUntil($scope.items[i].endDate);
	      }
	      $scope.$apply();
	  }, 1000);
});