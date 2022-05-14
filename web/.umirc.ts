import { defineConfig } from 'umi'

export default defineConfig({
  title: '智慧云平台',
  nodeModulesTransform: {
    type: 'none',
  },
  fastRefresh: {},
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true,
    },
  },
})
