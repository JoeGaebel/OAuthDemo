<template>
    <div id="app">
        <h1>OAuth Demo</h1>
        <p v-if="email">Hello! Welcome {{email}}</p>
        <p v-else>Redirecting...</p>
        <button v-if="email" v-on:click="update">Click me to update!</button>
        <p v-if="updateResponse">{{updateResponse}}</p>
    </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'app',
    data: () => ({
      email: '',
      updateResponse: ''
    }),
    async created() {
      axios.get('/api/users/me')
        .then(response => {
          if (response.status < 400) {
            this.email = response.data.email
            this.setCSRFToken(response.headers['x-csrf-token'])

            return
          }

          throw new Error()
        })
        .catch(() => {
          window.open(`/api/oauth2/authorization/google`, '_self')
        })
    },
    methods: {
      update() {
        axios.post('/api/users/update')
          .then(response => {
            this.updateResponse = response.data
          })
          .catch(err => {
            this.updateResponse = err
          })
      },
      setCSRFToken(token) {
        axios.defaults.headers.common['x-csrf-token'] = token
      }
    }
  }
</script>
