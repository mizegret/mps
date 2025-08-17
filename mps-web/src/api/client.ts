import createClient from 'openapi-fetch'
import type { paths } from './generated/schema'

export const api = createClient<paths>({
  baseUrl: import.meta.env.VITE_API_BASE_URL,
})
