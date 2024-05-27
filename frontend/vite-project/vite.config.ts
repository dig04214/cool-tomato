import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";
import { VitePWA } from "vite-plugin-pwa";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        react(),
        VitePWA({
            registerType: "autoUpdate",
            devOptions: {
                enabled: true,
            },
            manifest: {
                icons: [
                    {
                        src: "icons/logo192x192.png",
                        type: "image/png",
                        sizes: "192x192",
                    },
                    {
                        src: "icons/logo500x500.png",
                        type: "image/png",
                        sizes: "512x512",
                    },
                ],
            },
        }),
    ],
});
