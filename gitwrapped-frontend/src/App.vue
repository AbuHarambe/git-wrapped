<template>
  <div>
    <h1>GitHub Benutzerinfo</h1>

    <div v-if="!isLoggedIn">
      <button @click="loginWithGitHub">Mit GitHub einloggen</button>
    </div>

    <div v-else>
      <input v-model="username" placeholder="GitHub-Username" />
      <button @click="loadGitHubUser">Laden</button>

      <pre v-if="result">{{ result }}</pre>
      <p v-if="error" style="color: red">{{ error }}</p>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from "vue";
import axios from "axios";

export default defineComponent({
  name: "GitHubUserView",
  setup() {
    const username = ref("");
    const result = ref("");
    const error = ref("");
    const isLoggedIn = ref(false);

    const checkLogin = async () => {
      try {
        await axios.get("/api/me");
        isLoggedIn.value = true;
      } catch {
        isLoggedIn.value = false;
      }
    };

    const loginWithGitHub = () => {
      window.location.href = "http://localhost:8080/oauth2/authorization/github";
    };

    const loadGitHubUser = async () => {
      try {
        error.value = "";
        result.value = "";
        const response = await axios.get(`/api/github/${encodeURIComponent(username.value)}`);
        result.value = JSON.stringify(response.data, null, 2);
      } catch (e: any) {
        error.value = "Fehler beim Abrufen der GitHub-Daten.";
        console.debug(e);
      }
    };

    onMounted(checkLogin);

    return {
      username,
      result,
      error,
      isLoggedIn,
      loginWithGitHub,
      loadGitHubUser,
    };
  },
});
</script>
