router.factory('MessageService', function($http,  $state) {
	var messageService = {};
	
	messageService.sendMessage = function(token, userId, message){
		return $http.post('/api/user/' + userId + '/send-message', message, {headers: {'authToken': token}})
					.then(function (response){
						return response;
					});
		};
		
	messageService.getMessagesByType = function(token, userId, type){
		return $http.get('/api/user/' + userId + '/message?messageType=' + type,  {headers: {'authToken': token}})
					.then(function (response){
						return response;
					})
		
	}
	
	return messageService;
});