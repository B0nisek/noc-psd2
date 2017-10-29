(function(angular) {
  'use strict';
angular.module('tfTableTestApp').component('tfHeader', {
    bindings: {
        onLogout: "=",
        authToken: "<"
    },

  templateUrl: 'tribefire-js-components/tribefire-header/tribefire-header.html',
  controller: ("tribefireHeader", function($scope, $http){

      var onLogout = this.onLogout;
      var authToken = this.authToken;

      $scope.doLogout = function(){
          $http({
              method: 'GET',
              url: 'http://localhost:8080/psd/logout',
              headers: {
                  'auth-token' : '\'' + authToken + '\''
              }
          }).then(function successCallback(response) {
              onLogout(response);
          }, function errorCallback(response) {
              console.log(response);
          });
      };

  })
});
})(window.angular);