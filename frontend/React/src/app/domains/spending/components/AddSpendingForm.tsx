
import { CategoryCombobox } from "../../category/components/CategoryCombobox";
import { useTranslation } from 'react-i18next';
import { useState, useEffect } from 'react';
import type { CategoryData } from "../../category/schemas/Category";
import { Label } from "../../shared/components/label";
import { Input } from "../../shared/components/input";
import type { SpendingData } from "../schemas/Spending";
import { useForm} from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { createSpendingSchema } from "../schemas/Spending";
import { Button } from "../../shared/components/button";
import type { TypePeriodicData } from "../schemas/TypePeriodic";
import { TypePeriodicCombobox } from "./TypePeriodicCombobox";
import { EstablishmentCombobox } from "./EstablishmentCombobox";
import { addSpending } from "../application/SpendingService";
import { toast } from "sonner";
import type { EstablishmentData } from "../schemas/EstablishmentData";

//Define the props for the AddSpendingForm component
type AddSpendingFormProps = {
  categories: CategoryData[];
  typePeriodic: TypePeriodicData[];
  establishments: EstablishmentData[];
  loadEstablishment: () => void;
};

export function AddSpendingForm({ categories, typePeriodic, establishments, loadEstablishment }: AddSpendingFormProps) {
    const { t } = useTranslation();
    const schema = createSpendingSchema(t);
    // State to manage selected category and type periodic
    const [selectedCategory, setSelectedCategory] = useState<CategoryData | null>(null);
    const [selectedTypePeriodic, setSelectedTypePeriodic] = useState<TypePeriodicData | null>(null);
    const [selectedEstablishment, setSelectedEstablishment] = useState<EstablishmentData | null>(null);
    const {
        register,
        handleSubmit,
        reset,
        watch,
        setValue,
        formState: { errors, isSubmitting },
    } = useForm<SpendingData>({
        resolver: zodResolver(schema),
        defaultValues: {
            name: '',
            amount: undefined,
            isPeriodic: false,
            establishment: undefined,
        },
    });
    useEffect(() => {
        selectedTypePeriodic && setValue("typePeriodic", selectedTypePeriodic.id);
        selectedCategory && setValue("idCategory", selectedCategory.id);
        if (selectedEstablishment) {
            if (selectedEstablishment.id === 0) {
            // establecemos name como undefined
            setValue("establishment", {
                ...selectedEstablishment,
                name: ""
            } as EstablishmentData);
            } else {
            setValue("establishment", selectedEstablishment);
            }
        }
    }, [selectedCategory, selectedTypePeriodic, selectedEstablishment, setValue]);
    const isPeriodic = watch("isPeriodic");
    const onSubmit = async (formData: SpendingData) => {
        if (formData.establishment) {
            const { name, city, country } = formData.establishment;
            if (
            (name === "" || name === undefined) &&
            (city === "" || city === undefined) &&
            (country === "" || country === undefined)
            ) {
            formData.establishment = undefined;
            }
        }

        await addSpending(formData);
        toast.success(t('domains.spending.add.success', { name: formData.name, amount: formData.amount, category: selectedCategory?.name }));

        loadEstablishment();
        setSelectedCategory(null);
        setSelectedTypePeriodic(null);
        setSelectedEstablishment(null);
        reset();
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <div className="row-input">
                <div className="w-full">
                    <Label htmlFor="name" className="label">{t('domains.spending.name')}</Label>
                    <Input id="name" placeholder={t('domains.spending.namePlaceholder')} {...register('name', { required: t('domains.spending.errors.name.required') })} className="input-dark" />
                    {errors.name && (
                        <p className="text-red-500 text-sm">{errors.name.message}</p>
                    )}
                </div>
                <div className="w-full">
                <Label htmlFor="amount" className="label">{t('domains.spending.amount')}</Label>
                    <Input
                        type="number"
                        step="any"
                        id="amount"
                        placeholder={t('domains.spending.amountPlaceholder')}
                        {...register('amount', {
                            valueAsNumber: true,
                            required: t('domains.spending.errors.amount.required'),
                        })}
                        className="input-dark"
                    />
                </div>
            </div>
            <div className="row-input mt-10 ">
                <div className="w-full">
                    <Label htmlFor="category" className="label">{t('domains.spending.category')}</Label>
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
                    <Label htmlFor="date" className="label">{t('domains.spending.date')}</Label>
                    <Input
                        id="date"
                        type="date"
                        {...register("date", { required: t('domains.spending.errors.date.required') })}
                        className="input-dark"
                    />
                    {errors.date && (<p className="text-red-500 text-sm">{errors.date.message}</p>)}
                </div>
            </div>
            <div className="row-three-input mt-10">
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
                    <Label htmlFor="typePeriodic" className="label">{t('domains.spending.typePeriodic')}</Label>
                    <TypePeriodicCombobox
                        typePeriodic={typePeriodic}
                        selectedTypePeriodic={selectedTypePeriodic}
                        setSelectedTypePeriodic={setSelectedTypePeriodic}
                        disabled={!isPeriodic}
                    />
                    {errors.typePeriodic && (<p className="text-red-500 text-sm">{errors.typePeriodic.message}</p>)}
                </div>
                <div className="w-full">
                    <Label htmlFor="expirationDate" className="label">{t('domains.spending.expirationDate')}</Label>
                    <Input
                        disabled={!isPeriodic}
                        id="expirationDate"
                        type="date"
                        {...register("expirationDate")}
                        className="input-dark"
                    />
                    {errors.expirationDate && (<p className="text-red-500 text-sm">{errors.expirationDate.message}</p>)}
                </div>
            </div>
            <div className="row-three-input mt-10">
                <div className="w-full">
                    <Label htmlFor="establishment" className="label">{t('domains.establishment.name')}</Label>
                    <EstablishmentCombobox
                        establishments={establishments}
                        selectedEstablishment={selectedEstablishment}
                        setSelectedEstablishment={setSelectedEstablishment}
                    />
                </div>
                <div className="w-full">
                    <Label htmlFor="country" className="label">{t('domains.establishment.name')}</Label>
                    <Input
                        id="country"
                        type="text"
                        className="input-dark"
                        disabled={!selectedEstablishment || selectedEstablishment.id !== 0}
                        {...register("establishment.name")}
                        placeholder={t('domains.establishment.namePlaceholder')}
                    />
                </div>
                <div className="w-full">
                    <Label htmlFor="city" className="label">{t('domains.establishment.country')}</Label>
                    <Input
                        id="city"
                        type="text"                        
                        className="input-dark"
                        disabled={!selectedEstablishment || selectedEstablishment.id !== 0}
                        {...register("establishment.country")}
                        placeholder={t('domains.establishment.countryPlaceholder')}
                    />

                </div>
            </div>
            <div className="row-input mt-10">
                <div className="w-full">
                    <Label htmlFor="city" className="label">{t('domains.establishment.city')}</Label>
                    <Input
                        id="city"
                        type="text"                        
                        className="input-dark"
                        disabled={!selectedEstablishment || selectedEstablishment.id !== 0}
                        {...register("establishment.city")}
                        placeholder={t('domains.establishment.cityPlaceholder')}
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
