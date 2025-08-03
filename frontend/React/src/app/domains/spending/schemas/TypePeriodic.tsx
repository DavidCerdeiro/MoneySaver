import { z } from 'zod';

export const createTypePeriodicSchema = (t: (key: string) => string) => z.object({
  id: z.number().optional(),
  name: z.string().min(1, { message: t('errors.requiredField') }),
});

export type TypePeriodicData = z.infer<ReturnType<typeof createTypePeriodicSchema>>;