import { useState, useCallback } from 'react'
import { User } from '@/pages/admin/user'

export default () => {
  const [user, setUser] = useState<User | undefined>(undefined)

  const login = useCallback((user: User) => {
    setUser(user)
  }, [])

  const logout = useCallback(() => {
    setUser(undefined)
  }, [])

  return {
    user,
    login,
    logout,
  }
}
