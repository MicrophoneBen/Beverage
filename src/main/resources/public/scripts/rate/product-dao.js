
/*global define */

'use strict';

define(['./rate.module'], function (module) {
    module.service('productDao', ['$http',
        function ($http) {


            function getProduct(productId) {
                return $http.get('/v1/product_catalog/' + productId).then(function (result) {
                    return result.data;
                });
            }

            function getProducts(page, query) {
                return $http({
                    method: 'GET',
                    url: '/v1/product_catalog',
                    params: {
                        query: query,
                        page: page
                    }
                }).then(function (result) {
                    return result.data;
                });
            }

            return {
                getProduct: getProduct,
                getProducts: getProducts
            };

        }]);

});






