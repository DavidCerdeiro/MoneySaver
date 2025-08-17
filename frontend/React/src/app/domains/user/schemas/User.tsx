import { z } from "zod";

export const createUserSchema = (t: any, isEdit = false) =>
  z.object({
    id: z.number().optional(),
    idTypeChart: z.number().optional(),
    name: z.string().min(1, { message: t('domains.user.errors.name.required') }),
    surname: z.string().min(1, { message: t('domains.user.errors.surname.required') }),
    email: z.string().email({ message: t('domains.user.errors.email.invalid') }),
    favouriteGraph: z.number().optional(),
    password: isEdit
      ? z
          .string()
          .optional()
          .refine(
            (val) =>
              !val || /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/.test(val),
            { message: t('errors.passwordChars') }
          )
      : z
          .string()
          .min(8, { message: t('errors.passwordLength') })
          .regex(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/, {
            message: t('errors.passwordChars'),
          }),
    confirmPassword: isEdit
      ? z.string().optional()
      : z.string(),
  })
  .refine(
    (data) =>
      !data.password || data.password === data.confirmPassword,
    {
      message: t('errors.passwordMismatch'),
      path: ['confirmPassword'],
    }
  );

export type UserData = z.infer<ReturnType<typeof createUserSchema>>;