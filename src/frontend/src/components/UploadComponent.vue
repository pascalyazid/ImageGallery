<template>
  <main>
    <form
        action="/api/images/upload"
        method="POST"
        enctype="multipart/form-data">
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
    </form>
  </main>


</template>

<script>
import axios from "axios";

export default {
  name: "UploadComponent",
  props: {},
  data: () => ({
    form: {
      desc: '',
      file: '',
      date: '',
    }
  }),
  methods: {
    upload(e) {
      e.preventDefault();
      let formData = new FormData();
      formData.append('file', this.form.file);
      formData.append('desc', this.form.desc);
      formData.append('date', this.form.date);
      axios.post('/api/images/upload', formData, {
        headers: {
          'Content-Type' : 'multipart/form-data'
        }
      })
          .then((response) => {
            console.log(response)
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {
            console.log("lol")
          })
    },
    handelFile ( event ) {
      this.form.file = event.target.files[0];
      console.log(this.form)
    }
  }
}
</script>

<style scoped>

</style>