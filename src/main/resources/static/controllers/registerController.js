var registerController = router.controller('registerController', function($scope, $http) {
    $scope.user = {};
    $scope.register = function() {
        $scope.user.registrationDate = new Date();
        if ($scope.user.username == '')
            $scope.user.username = null;
        $http.post('/api/user', $scope.user).success((res) => {
            alert(res.error || "Success!");
        }).error(function(data, response) {
            alert(data.message);
        });
    };
});
