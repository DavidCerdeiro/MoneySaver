import { z } from "zod";

export const EstablishmentDataSchema = z.object({
  id: z.number().optional(),
  name: z.string().optional(),
  country: z.string().optional(),
  city: z.string().optional()
});

export type EstablishmentData = z.infer<typeof EstablishmentDataSchema>;
