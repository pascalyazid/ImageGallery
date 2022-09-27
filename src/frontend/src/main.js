import { createApp } from 'vue'
import VueAxios from "vue-axios";
import App from "@/App";
import axios from "axios";
const app = createApp(App)
app.use(VueAxios, axios)
app.mount('#app')