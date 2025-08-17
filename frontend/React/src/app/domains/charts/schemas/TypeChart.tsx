import { z } from 'zod';

export const createTypeChartSchema = (t: (key: string) => string) => z.object({
  id: z.number().optional(),
  name: z.string().min(1, { message: t('errors.requiredField') }),
});

export type TypeChartData = z.infer<ReturnType<typeof createTypeChartSchema>>;