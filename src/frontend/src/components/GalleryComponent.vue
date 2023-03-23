<template>
  <main>
    <ul>
      <li v-for="(image, i) in images" :key="i">
        <img :src="blobs[i]" fluid :alt="image.desc" width="300" height="200" @click="openImage(i)">
      </li>
    </ul>
  </main>
</template>
<script>

import axios from "axios";

export default {
  name: 'GalleryComponent',
  props: {
  },
  data: () => ({
    images: [],
    blobs: [],
  }),

  methods: {
    openImage(index) {
      window.open(this.blobs[index], '_blank');
    }
  },

  async mounted() {

    await fetch('/api/images/list')
      .then((response) => response.json())
      .then((data) => {
        this.images = data;
      })
    console.log(this.images)
    for (let img of this.images) {
      console.log(img.id);

      const config = { url: "/api/images/load?id=" + img.id, method: "get", responseType: "blob" }
      const response = await axios.request(config)
      this.blobs.push(URL.createObjectURL(response.data));
    }
  },

  beforeUnmount() {
    this.blobs.forEach(({ imgUrl }) => {
      URL.revokeObjectURL(imgUrl);
    })
  }
}
</script>
