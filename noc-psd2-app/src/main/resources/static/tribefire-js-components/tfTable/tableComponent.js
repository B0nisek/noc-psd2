(function(angular) {
  'use strict';
angular.module('tfTableTestApp').component('tfTable', {
  bindings: {
    access: '@?',
    type: '@?',
    paging: '@?',
    showProperties: '@?',
    jsonArray: '<',
    tfDebug: '<',
    ngHide: '<'
  },

  templateUrl: 'tribefire-js-components/tfTable/tfTable.html',
  controller: ("tfTableCtrl",function($scope){

      $scope.tableMetaData = {};
      $scope.tableMetaData.paging = parseInt(this.paging, 10);
      $scope.tableMetaData.order = "date";
      $scope.tableMetaData.page = 1;

      this.tfDebug == null ? $scope.tfDebug = false : $scope.tfDebug = this.tfDebug;

      if(this.showProperties != null){
          $scope.tableMetaData.showProperties = this.showProperties.split(",");
      } else {
          $scope.tableMetaData.showProperties = [];
      }

      $scope.tableMetaData.jsonArray = this.jsonArray;

      this.doInit = function(){
          jsonInstanceBrowsing();
      };

      this.$onInit = function () {
          this.doInit();
      };

      this.$onChanges = function(changesObj){
          if(changesObj.jsonArray != null){
              $scope.jsonArray = changesObj.jsonArray.currentValue;
              jsonInstanceBrowsing();
          }
      };


    /*
    * Color coding for entries
    *   if it is a number return green when it is bigger and red if it is smaller than 0
    *   (0 = black)
    */
    $scope.colorCoding = function(element){
      if(typeof element === 'number'){
        if(element > 0){
          return {
            "color" : "green"
          } 
        }else if(element < 0){
          return {
            "color" : "red"
          } 
        }
      }
      return;

    };

    /*
    *   On Reorder
    */
    $scope.onReorder = function(order){  

    };

    /*
    *   On Paginate
    */
    $scope.onPaginate = function(page,rows){        

    };

    /*
    *   Full text search
    */
    $scope.fullTextSearch = function(){
        
    };


   /* output parameters */
    var instanceBrowsingOutput = new Object();

    function propertyActive(propertyName){
        for (var i = 0; i < $scope.tableMetaData.showProperties.length; i++) {
            if($scope.tableMetaData.showProperties[i] == propertyName){
                return true;
            }
        }

        return false;
    }

    // function formatDateProperty(date){
      //     if(date == null){
      //         return;
      //     }
      //
      //     return date.getDate() + "/" + (date.getMonth()+1) + "/" + date.getFullYear() +
      //         " " + date.getHours() + ":" + date.getMinutes() + ":" + (date.getSeconds() == 0 ? "00" : date.getSeconds());
      // }

    function preparePropertyList(){
        $scope.propertyList = [];

        for(var j = 0; j < $scope.jsonArray.length; j++){
            var displayObj = {};

            for(var i = 0; i < $scope.tableMetaData.showProperties.length; i++){
                for(var property in $scope.jsonArray[j]){
                    if($scope.tableMetaData.showProperties[i] === property){
                        // if("[object Date]" === Object.prototype.toString.call($scope.jsonArray[j][property])){
                        //     displayObj[property] = formatDateProperty($scope.jsonArray[j][property]);
                        //     continue;
                        // }

                        displayObj[property] = $scope.jsonArray[j][property];
                    }
                }
            }

            displayObj['date'] = $scope.jsonArray[j]['date'];
            displayObj['amount'] = $scope.jsonArray[j]['amount'];
            $scope.propertyList.push(displayObj);
        }
    }

    function jsonInstanceBrowsing(){
        if($scope.tfDebug){
            console.log($scope.jsonArray);
        }

        var entity = $scope.jsonArray[0];

        var entityProperties = new Object();

        for(var i = 0; i < $scope.tableMetaData.showProperties.length; i++){
            for(var property in entity){
                if($scope.tableMetaData.showProperties[i] === property){
                    entityProperties[property] = propertyActive(property);
                }
            }
        }

        instanceBrowsingOutput.entityProperties = entityProperties;
        $scope.entityProperties = entityProperties;

        $scope.tableMetaData.length = $scope.jsonArray.length;
        preparePropertyList();
    }

    /**
    * Angular UI bindings
    */
    $scope.entityProperties = {}; // table header containing key:value pairs. The key = the property name, value = boolean to show/hide the table column
    $scope.toggleProperty = function (propertyName) {
        $scope.entityProperties[propertyName] = !$scope.entityProperties[propertyName]
    };

    $scope.propertyList = []; // table data
    $scope.selected = []; // holds the table items
    $scope.openMenu = function ($mdOpenMenu, ev) { // trigger the table open menu event
        originatorEv = ev;
        $mdOpenMenu(ev);
    };
    $scope.checked = true;
    $scope.uncheck = function () {
        $scope.checked = !$scope.checked;
    };
  })
});
})(window.angular);