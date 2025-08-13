import '@/styles/utilities.css';

import { Card, CardAction, CardContent, CardFooter, CardHeader, CardTitle, CardDescription } from "@/app/domains/shared/components/card.tsx";
import { Input } from "@/app/domains/shared/components/input";
import { Button } from "@/app/domains/shared/components/button";
import { Label } from "@/app/domains/shared/components/label.js";
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useForm } from "react-hook-form"
import { zodResolver } from "@hookform/resolvers/zod";
import { createSignUpSchema } from "@/app/domains/auth/schemas/SignUp";
import type { SignUpFormData } from "@/app/domains/auth/schemas/SignUp";
import { signUpUser } from "../application/AuthService";
import { useNavigate } from 'react-router-dom';
import { useUser } from "@/app/contexts/UserContext.tsx";
import { toast } from "sonner";

export function SignUpForm() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const schema = createSignUpSchema(t);
    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
    } = useForm<SignUpFormData>({
        resolver: zodResolver(schema),
    });
    const { setUser } = useUser();

    const onSubmit = async (data: SignUpFormData) => {
        try {
            const requestBody: SignUpFormData = {
                ...data,
            };
            const { confirmPassword, ...userData } = requestBody;
            const result = await signUpUser(userData);
            setUser(result);
            toast.info(t('signUp.success', {name: result.name}));
            // After successful signup, redirect to the authUser page
            navigate('/authUser');
        } catch (error) {
            toast.error(t('signUp.error'));
        }
    };
    return (
            <Card className="form-card">
                <CardHeader>
                    <CardTitle className="text-2xl">{t('signUp.title')}</CardTitle>
                    <CardDescription className="card-description">{t('signUp.welcome')}</CardDescription>
                    <CardAction>
                        <Link to="/login" className="text-link">{t('signUp.logIn')}</Link>
                    </CardAction>
                </CardHeader>

                <CardContent className="grid gap-4">
                    <form onSubmit={handleSubmit(onSubmit)} className="grid gap-4 md:gap-6">
                        <div className="grid gap-2">
                            <Label htmlFor="name">{t('domains.user.name')}</Label>
                            <Input id="firstName" placeholder={t('signUp.namePlaceholder')} {...register("name")} className="input-dark" />
                            {errors.name && <p className="text-red-500 text-sm">{errors.name.message}</p>}
                        </div>
                        <div className="grid gap-2">
                            <Label htmlFor="surname">{t('domains.user.surname')}</Label>
                            <Input id="surname" placeholder={t('signUp.lastNamePlaceholder')} {...register("surname")} className="input-dark" />
                            {errors.surname && <p className="text-red-500 text-sm">{errors.surname.message}</p>}
                        </div>
                        <div className="grid gap-2">
                            <Label htmlFor="email">{t('domains.user.email')}</Label>
                            <Input id="email" type="email" placeholder={t('signUp.emailPlaceholder')} {...register("email")} className="input-dark" />
                            {errors.email && <p className="text-red-500 text-sm">{errors.email.message}</p>}
                        </div>
                        <div className="grid gap-2">
                            <Label htmlFor="password">{t('domains.user.password')}</Label>
                            <Input id="password" type="password" {...register("password")} className="input-dark" />
                            {errors.password && <p className="text-red-500 text-sm">{errors.password.message}</p>}
                        </div>
                        <div className="grid gap-2">
                            <ul className="password-requirements">
                                <li>{t('domains.user.passwordRequirements.length')}</li>
                                <li>{t('domains.user.passwordRequirements.uppercase')}</li>
                                <li>{t('domains.user.passwordRequirements.lowercase')}</li>
                                <li>{t('domains.user.passwordRequirements.number')}</li>
                            </ul>
                        </div>
                        
                        <div className="grid gap-2">
                            <Label htmlFor="confirmPassword">{t('signUp.confirmPassword')}</Label>
                            <Input id="confirmPassword" type="password" {...register("confirmPassword")} className="input-dark" />
                            {errors.confirmPassword && <p className="text-red-500 text-sm">{errors.confirmPassword.message}</p>}
                        </div>
                        <Button type="submit" className="button-green" disabled={isSubmitting}>
                            {t('signUp.submit')}
                        </Button>
                    </form>
                </CardContent>

                <CardFooter>
                </CardFooter>
            </Card>
    );
}