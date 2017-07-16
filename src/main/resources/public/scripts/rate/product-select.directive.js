'use strict';

define(['./rate.module'], function ( module) {


    module.directive('selectProduct', function () {

        return {
            restrict: 'E',
            scope: {},
            bindToController: {
                selected: '='
            },
            templateUrl: 'scripts/rate/product-select.directive.template.html',
            controllerAs: 'vm',
            controller: function ($http) {
                var vm = this;

                vm.getBeverages = getBeverages;
                vm.getDisplayName = getDisplayName;
                vm.page = 0;

                function getBeverages($select, $event) {
                    if (vm.loading) {
                        return;
                    }
                    // no event means first load!
                    if (!$event) {
                        vm.beverages = [];
                        vm.page = 0;
                    } else {
                        ;
                        vm.page += 1;
                    }

                    vm.loading = true;
                    $http({
                        method: 'GET',
                        url: '/v1/product_catalog',
                        params: {
                            query: $select.search,
                            page: vm.page
                        }
                    }).then(function (resp) {
                        vm.beverages = vm.beverages.concat(resp.data);
                    })['finally'](function () {
                        vm.loading = false;
                    });
                }
                ;

                function getDisplayName(beverage) {
                    if (!beverage)
                        return "";
                    return beverage.name + ', ' + beverage.producer + ',' + beverage.originCountry + (beverage.vintage ? (', ' + beverage.vintage) : "");
                }
                ;
            }
        };
    });



    
});


