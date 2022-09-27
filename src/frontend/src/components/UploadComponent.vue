<template>
<div>
  <section class="form">
    <div class="field">
      <label class="label">Name</label>
      <div class="control">
        <input v-model="form.desc" class="input" type="text" placeholder="Name">
      </div>
    </div>
    <div class="field">
      <label class="label">File</label>
      <div class="control">
        <input v-model="form.fili" class="input" type="file" placeholder="File">
      </div>
    </div>
    <div class="field">
      <label class="label">Date</label>
      <div class="control">
        <input v-model="form.date" class="input" type="date">
      </div>
    </div>
    <div class="field">
      <div class="control">
        <input type="submit" value="Upload">
      </div>
    </div>
  </section>
</div>
</template>

<script>
export default {
  name: "UploadComponent",
  props:{},
  data: () => ({
    form: {
      desc: '',
      file: '',
      date: '',
    }
  }),
  methods : {
    upload () {
      const payload = new FormData(this.form)
      fetch('/api/images/upload', {
        method: 'POST',
        body: payload
      })
          .then(function (response) {
            if(response.status == 409) {
              alert("Image already exists");
            }
            if (!response.ok) {
              alert("Invalid input");
            } else {
              alert("Image saved");
            }
          })
          .catch(function (error) {
            console.log(error);
          });
    }
  }
}
</script>

<style scoped>

</style>