module.exports = {
    webpack: function(config) {
        config.module.rules.push({
            test: /\.js$/,
            enforce: 'pre',
            use: [
                {
                    loader: 'source-map-loader',
                    options: {
                        filterSourceMappingUrl: (url, resourcePath) => {
                            // Suppress warnings for the specified packages
                            return !resourcePath.includes('@react-keycloak/core') &&
                                !resourcePath.includes('@react-keycloak/web');
                        }
                    }
                }
            ]
        });
        return config;
    }
};


  
