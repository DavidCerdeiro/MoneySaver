// components/ui/toaster.tsx

import { ToastProvider } from "@radix-ui/react-toast"
import { Toaster as SonnerToaster } from "sonner"

export function Toaster() {
  return (
    <ToastProvider>
      <SonnerToaster
        position="top-center"
        richColors
        closeButton
        duration={3000}
      />
    </ToastProvider>
  )
}
