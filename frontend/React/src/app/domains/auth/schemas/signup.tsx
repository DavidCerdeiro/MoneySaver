import { z } from "zod";
 
// Schema for Sign Up form validation
export const createSignUpSchema  = (t: (key: string) => string) => z.object({
  locale: z.string().optional(),
  name: z.string().min(1, { message: t('errors.requiredField') }),
  surname: z.string().min(1, { message: t('errors.requiredField') }),
  email: z.string().email({ message: t('errors.requiredField') }),
  password: z.string().min(8, { message: t('errors.passwordLength') })
  .regex(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/, {
    message: t('errors.passwordChars') ,
    }),
  confirmPassword: z.string().min(8, { message: t('errors.passwordLength') }),
}).refine((data) => data.password === data.confirmPassword, {
  message: t('errors.passwordMismatch'),
  path: ["confirmPassword"],
}

);

export type SignUpFormData = z.infer<ReturnType<typeof createSignUpSchema>>;