<template>
  <main>
    <form v-on:submit.prevent="upload">
      <div class="form-control">
        <label>Description</label>
        <input type="text" v-model="form.desc" name="desc" id="name" placeholder="Description">
      </div>
      <div class="form-control">
        <label>File</label>
        <input type="file" v-on:change="handelFile" name="file" id="file">
      </div>
      <div class="form-control">
        <label>Date</label>
        <input type="date" v-model="form.date" name="date" id="date">
      </div>
      <div class="form-control">
        <input type="submit" value="Upload">
      </div>
      <div class="message">
        <p v-on:change="message">
          {{ message }}
        </p>
      </div>
    </form>
  </main>


</template>

<script>
import axios from "axios";
import App from "@/App";

export default {
  name: "UploadComponent",
  props: {},
  data: () => ({
    file: '',
    form: {
      desc: '',
      path: '',
      date: '',
    },
    message: ''
  }),
  methods: {
    upload(e) {
      e.preventDefault();
      let formData = new FormData();
      formData.append('file', this.file);
      formData.append('desc', this.form.desc);
      formData.append('path', this.form.path);
      formData.append('date', this.form.date);
      axios.post('/api/images/upload', formData, {
        headers: {
          'Content-Type' : 'multipart/form-data'
        }
      })
          .then((response) => {
            if(response.status == 400) {
              this.message = "Invalid Input"
            }
            if(response.status == 409) {
              this.message = "Image already exists"
            }
            if(response.status == 200) {
              this.$root.togg
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {
            console.log("lol")
          })
    },
    handelFile ( event ) {
      this.file = event.target.files[0];
      this.form.path= event.target.files[0]['name'];
      console.log(this.form)
    }
  }
}
</script>

<style scoped>

</style>