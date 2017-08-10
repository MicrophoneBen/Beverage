'use strict';

define(['./review.module'], function (module) {


   module.directive('productDetails', function () {

        return {
            restrict: 'E',
            scope: {},
            bindToController: {
                product: '='
            },
            templateUrl: 'scripts/review/product-details.directive.template.html',
            controllerAs: 'vm',
            controller: function () {

            }
        };
    });
            
});





