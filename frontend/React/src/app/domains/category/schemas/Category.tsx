import { z } from "zod";

export const createCategorySchema  = (t: (key: string) => string) => z.object({
    id: z.number().optional(),
    idUser: z.number().optional(),
    name: z.string().min(1, { message: t('errors.requiredField') }),
    icon: z.string().optional(),
})

export type CategoryData = z.infer<ReturnType<typeof createCategorySchema>>;