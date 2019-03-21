<template>
    <div id="app">
        <h1>OAuth Demo</h1>
        <p v-if="email">Hello! Welcome {{email}}</p>
        <p v-else>Redirecting...</p>
    </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'app',
    data: () => ({ email: '' }),
    async created() {
      axios.get('/api/users/me')
        .then(response => {
          if (response.status < 400) {
            this.email = response.data.email
            return
          }

          throw new Error()
        })
        .catch(() => {
          window.open(`/api/oauth2/authorization/google`, '_self')
        })
    }
  }
</script>
