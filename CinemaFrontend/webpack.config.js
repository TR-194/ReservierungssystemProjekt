const { DefinePlugin } = require('webpack');

module.exports = {
  plugins: [
    new DefinePlugin({
      global: 'window' // Fix "global is not defined"
    })
  ],
  resolve: {
    fallback: {
      crypto: require.resolve("crypto-browserify"), 
      stream: require.resolve("stream-browserify"),
      buffer: require.resolve("buffer/")
    }
  }
};