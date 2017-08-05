
/*global define */

'use strict';

define(['./rate.module'], function (module) {
    module.service('rateDao', ['$http',
        function ($http) {
            
            function getRatesWithPageAndQuery(page, query) {
                return $http.get('/v1/rate', {
                    params: {
                        page: page?page:0,
                        query: query
                    }
                }).then(function (result) {
                    return result.data;
                });
            }
            
            function getRates() {
                return getRatesWithPageAndQuery();
            }
            
            function deleteRate(rateId) {
                return $http.delete('/v1/rate/' + rateId);
            }
            
            function updateRate(rateId, rate) {
                return $http.put('/v1/rate/' + rateId, rate);
            }
            
            function createRate(rate) {
                var rateDto = {
                    description: rate.description,
                    rate: rate.score ? rate.score : 0,
                    productId: rate.productId
                };
                return $http.post('/v1/rate', rateDto);
            }
            
            return {
                getRatesWithPageAndQuery: getRatesWithPageAndQuery,
                getRates: getRates,
                deleteRate:deleteRate,
                updateRate: updateRate,
                createRate: createRate
            };

        }]);

});






