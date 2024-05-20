module.exports = {

    webpack: function(config, env) {
      return config;
    },
   
    devServer: function(configFunction) {
      return function(proxy, allowedHost) {
        
        const config = configFunction(proxy, allowedHost);
  
       
        if (config.onBeforeSetupMiddleware) {
          delete config.onBeforeSetupMiddleware;
        }
        if (config.onAfterSetupMiddleware) {
          delete config.onAfterSetupMiddleware;
        }
        
        
        config.setupMiddlewares = (middlewares, devServer) => {
         
          return middlewares;
        };
  
        return config;
      };
    }
  };
  
