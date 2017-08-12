
/*global define */

'use strict';

define(['./review.module', './review-create-controller', './review-list-controller', './product-show-controller', 
    './product-list-controller'], function (module) {
    module.controller('reviewCtrl', ['$scope',
        function ($scope) {
            var vm = this;

            activate();

            $scope.$on('reviewCreated', function (event, data) {
                selectTab(1);
            });

            $scope.$on('productSelected', function (event, data) {
                selectTab(3);
            });

            function activate() {

            }

            function selectTab(tab) {
                vm.activeTab = tab;
            }

        }]);

});



