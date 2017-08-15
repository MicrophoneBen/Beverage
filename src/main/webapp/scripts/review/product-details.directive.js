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
                var vm = this;
                
                vm.getRate = getRate;

                //TODO dupplicated code refactory needed
                function getRate(rate) {
                    if (!rate)
                        return "unrated";
                    if (rate > 7) {
                        return "good";
                    } else if (rate > 3) {
                        return "medium";
                    } else {
                        return "bad";
                    }
                }
            }
        };
    });

});





