(function(angular) {
  'use strict';

angular.module('multibankApp').component('login', {
  bindings: { 
    onLogin: "=",
    autoLogin: "<"
  },
  templateUrl: 'components/login/login.html',
  controller: ("loginCtrl",function($scope, $http){

    this.$onInit = function(){
      $scope.autoLogin = this.autoLogin;
    };

    var onLogin = this.onLogin;

    $scope.username = '';
    $scope.password = '';
    $scope.logginError = false;

    $scope.doLogin = function(){
        $http({
            method: 'POST',
            url: 'http://localhost:8080/psd/login',
            data: {
                username: $scope.username,
                password: $scope.password
            }
        }).then(function successCallback(response) {
            onLogin(response);
            $scope.logginError = false;
            $scope.username = '';
            $scope.password = '';
        }, function errorCallback(response) {
            $scope.logginError = true;
            console.log(response);
        });
    };
})
});
})(window.angular);