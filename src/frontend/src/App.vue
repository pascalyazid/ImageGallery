<template>
  <main>
    <div id="app" className="context"></div>
    <header>
      <h1>Image Gallery</h1>
      <p>Service for uploading and hosting images with Spring Boot and Nuxt.js</p>
    </header>
    <main>
      <button @click="toggleGallery">Open Gallery</button>
      <button @click="toggleUpload">Upload Image</button>
      <GalleryComponent
          v-if="showGallery"
      />
      <UploadComponent
          v-if="showUpload"
      />
    </main>
  </main>
</template>

<script>
import GalleryComponent from "@/components/GalleryComponent";
import UploadComponent from "@/components/UploadComponent";

export default {
  name: "App",
  components: {
    UploadComponent,
    GalleryComponent
  },

  data: () => ({
    showGallery: false,
    showUpload: false
  }),

  methods: {
    toggleGallery() {

        this.showGallery = true;
        this.showUpload = false;
    },
    toggleUpload() {

        this.showGallery = false;
        this.showUpload = true;
    }
  }
}
</script>