import { CategoryCombobox } from "../../category/components/CategoryCombobox";
import { useTranslation } from 'react-i18next';
import { useState } from 'react';
import type { CategoryData } from "../../category/schemas/Category";
import { Label } from "../../shared/components/label";
import { Input } from "../../shared/components/input";
import type { SpendingData } from "../schemas/Spending";
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { createSpendingSchema } from "../schemas/Spending";
import { Button } from "../../shared/components/button";
import type { TypePeriodicData } from "../schemas/TypePeriodic";
import { TypePeriodicCombobox } from "./TypePeriodicCombobox";
import { addSpending } from "../application/SpendingService";
import { toast } from "sonner";

//Define the props for the AddSpendingForm component
type AddSpendingFormProps = {
  categories: CategoryData[];
  typePeriodic: TypePeriodicData[];
};

export function AddSpendingForm({ categories, typePeriodic }: AddSpendingFormProps) {
    const { t } = useTranslation();
    const schema = createSpendingSchema(t);
    // State to manage selected category and type periodic
    const [selectedCategory, setSelectedCategory] = useState<CategoryData | null>(null);
    const [selectedTypePeriodic, setSelectedTypePeriodic] = useState<TypePeriodicData | null>(null);
    const {
        register,
        handleSubmit,
        reset,
        watch,
        formState: { errors, isSubmitting },
    } = useForm<SpendingData>({
        resolver: zodResolver(schema),
        defaultValues: {
            name: '',
            amount: undefined,
            isPeriodic: false,
        },
    });
    // Watch the isPeriodic field to conditionally enable/disable type periodic and expiration date fields
    const isPeriodic = watch("isPeriodic");

    const onSubmit = async (formData: SpendingData) => {
        const requestBody: SpendingData = {
            ...formData,
            idCategory: selectedCategory?.id,
            typePeriodic: selectedTypePeriodic?.id,
            idUser: 1, // Assuming a static user ID for now, replace with actual user ID logic
        };
        
        await addSpending(requestBody);

        console.log("Spending added successfully");
        // Reset form and state after submission
        toast.success(t('domains.spending.add.success', { name: formData.name, amount: formData.amount, category: selectedCategory?.name }));
        setSelectedCategory(null);
        setSelectedTypePeriodic(null);
        reset();
    };
    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <div className="row-input">
                <div className="w-full">
                    <Label htmlFor="name">{t('domains.spending.name')}</Label>
                    <Input id="name" placeholder={t('domains.spending.namePlaceholder')} {...register('name', { required: t('domains.spending.errors.name.required') })} className="input-dark" />
                    {errors.name && (
                        <p className="text-red-500 text-sm">{errors.name.message}</p>
                    )}
                </div>
                <div className="w-full">
                <Label htmlFor="amount">{t('domains.spending.amount')}</Label>
                    <Input type="number" id="amount" placeholder={t('domains.spending.amountPlaceholder')} {...register('amount', {valueAsNumber: true, required: t('domains.spending.errors.amount.required')})} className="input-dark" />
                    {errors.amount && (
                        <p className="text-red-500 text-sm">{errors.amount.message}</p>
                    )}
                </div>
            </div>
            <div className="row-input mt-10 ">
                <div className="w-full">
                    <Label htmlFor="category">{t('domains.spending.category')}</Label>
                    <CategoryCombobox
                        categories={categories}
                        selectedCategory={selectedCategory}
                        setSelectedCategory={setSelectedCategory}
                        setSelectedEmoji={() => {}}
                        setSelectedIdEmoji={() => {}}
                        setEmojiIsNative={() => {}}
                        setValue={() => {}}
                    />
                </div>
                <div className="w-full">
                    <Label htmlFor="date">{t('domains.spending.date')}</Label>
                    <Input
                        id="date"
                        type="date"
                        {...register("date", { required: t('domains.spending.errors.date.required') })}
                        className="input-dark"
                    />
                </div>
            </div>
            <div className="row-input-periodic mt-10">
                <div className="w-full flex items-center gap-3">
                    <input
                        type="checkbox"
                        id="isPeriodic"
                        {...register("isPeriodic")}
                        className="w-4 h-4"
                    />
                    <Label htmlFor="isPeriodic">{t('domains.spending.periodicity')}</Label>
                </div>
                
                <div className="w-full">
                    <Label htmlFor="typePeriodic">{t('domains.spending.typePeriodic')}</Label>
                    <TypePeriodicCombobox
                        typePeriodic={typePeriodic}
                        selectedTypePeriodic={selectedTypePeriodic}
                        setSelectedTypePeriodic={setSelectedTypePeriodic}
                        disabled={!isPeriodic}
                    />
                </div>
                <div className="w-full">
                    <Label htmlFor="expirationDate">{t('domains.spending.expirationDate')}</Label>
                    <Input
                        disabled={!isPeriodic}
                        id="expirationDate"
                        type="date"
                        {...register("expirationDate")}
                        className="input-dark"
                    />
                </div>
            </div>
            <div className="row-input mt-10">
                <Button type="submit" disabled={isSubmitting}>
                    {t('domains.spending.add.submit')}
                </Button>
            </div>
        </form>
    );
}
