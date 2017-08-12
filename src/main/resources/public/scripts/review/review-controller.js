
/*global define */

'use strict';

define(['./review.module', './product-select.directive', './product-details.directive'
            , '../utility/beverage-utility', './review-dao', './product-dao', './review-create-controller', './review-list-controller'
            , './product-show-controller'], function (module) {
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



