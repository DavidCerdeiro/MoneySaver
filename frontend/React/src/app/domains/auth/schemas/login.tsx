import { z } from "zod";
 
// Schema for Log In form validation
export const createLogInSchema  = (t: (key: string) => string) => z.object({
  email: z.string().email({ message: t('errors.requiredField') }),
  password: z.string().min(1, { message: t('errors.requiredField') }),
});

export type LogInFormData = z.infer<ReturnType<typeof createLogInSchema>>;