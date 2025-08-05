import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { useTranslation } from 'react-i18next';
import { Button } from '@/app/domains/shared/components/button';
import { Input } from '@/app/domains/shared/components/input';
import { Label } from '@/app/domains/shared/components/label';
import { createCategorySchema, type CategoryData } from '../schemas/Category';
import { addCategory } from '../application/CategoryService';
import { useState } from 'react';
import { toast } from 'sonner';
import { getEmojiById } from './EmojiFunctions';
import data from '@emoji-mart/data';
import Picker from '@emoji-mart/react';


export function AddCategoryForm({idUser}: {idUser: number | undefined}) {
  const { t } = useTranslation();

  const [selectedEmoji, setSelectedEmoji] = useState<string>('💲');
  const [selectedIdEmoji, setSelectedIdEmoji] = useState<string>('heavy_dollar_sign');
  const schema = createCategorySchema(t);
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm({
    resolver: zodResolver(schema),
    defaultValues: {
      icon: selectedIdEmoji,
      idUser: idUser ? idUser : 1,
    },
  });

  const onSubmit = async (formData: CategoryData) => {
    try {
      const requestBody: CategoryData = {
        ...formData,
        icon: selectedIdEmoji,
      };
      await addCategory(requestBody);
      const nativeEmoji = getEmojiById(selectedIdEmoji);
      toast.success(t('domains.category.add.success', {icon: nativeEmoji, name: formData.name}));
      reset();
      setSelectedEmoji('💲'); // Emoji por defecto
      setSelectedIdEmoji('heavy_dollar_sign'); // ID por defecto
    } catch (error) {
      console.error('Error adding category:', error);
      toast.error(t('domains.category.add.error'));
    }
  };

  return (
    <div className='flex flex-col items-center justify-center px-4'>
      <h1 className="page-title">{t('header.sections.spendings.addCategory.title')}</h1>
      <p  className="page-description">{t('domains.category.add.description')}</p>
      <form onSubmit={handleSubmit(onSubmit)} className="grid gap-4 md:gap-6">
        <div className="grid gap-2">
          <Label htmlFor="name">{t('domains.category.name')}</Label>
          <Input id="name" {...register('name', { required: t('domains.category.errors.name.required') })} className="input-dark" />
          {errors.name && (
            <p className="text-red-500 text-sm">{errors.name.message}</p>
          )}
        </div>

        <div className="grid gap-2">
          <Label>{t('domains.category.icon')}</Label>
          <div className="flex items-center gap-2">
            <span className="text-2xl">{t('domains.category.add.iconSelected')}{selectedEmoji}</span>
          </div>
          <Picker
            data={data}
            onEmojiSelect={(emoji: any) => {
                setSelectedEmoji(emoji.native);
                setSelectedIdEmoji(emoji.id);
            }}
            previewPosition="none"
          />
        </div>

        <input type="hidden" {...register('icon')} />

        <Button type="submit" className="button-green" disabled={isSubmitting}>
          {t('domains.category.add.submit')}
        </Button>
      </form>
    </div>
  );
}
