import React from 'react';
import { Card, CardAction, CardContent, CardFooter, CardHeader, CardTitle, CardDescription } from "@/app/domains/shared/card";
import { Input } from "@/app/domains/shared/input.js";
import { Button } from "@/app/domains/shared/button.js";
import { Label } from "@/app/domains/shared/label.js";
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useForm } from "react-hook-form"
import { zodResolver } from "@hookform/resolvers/zod";
import { createSignUpSchema } from "../schemas/signup.tsx";
import type { SignUpFormData } from "../schemas/signup";
import { signUpUser } from "../application/authService";
import { z } from "zod";


export function SignUpForm() {
    const { t } = useTranslation();
    const schema = createSignUpSchema(t);
    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
    } = useForm<SignUpFormData>({
        resolver: zodResolver(schema),
    });

    const onSubmit = async (data: SignUpFormData) => {
        try {
            const { confirmPassword, ...userData } = data;
            const result = await signUpUser(userData);
            console.log("Usuario registrado:", result);
        } catch (error) {
            console.error("Signup error:", error);
        }
    };
    return (
        <div className="min-h-screen flex items-center justify-center px-4">
            <Card className="w-full max-w-sm">
                <CardHeader>
                    <CardTitle className="text-2xl">{t('signUp.title')}</CardTitle>
                    <CardDescription>{t('signUp.welcome')}</CardDescription>
                    <CardAction>
                        <Link to="/login" className="px-0">{t('signUp.logIn')}</Link>
                    </CardAction>
                </CardHeader>
                <CardContent className="grid gap-4">
                    <form onSubmit={handleSubmit(onSubmit)} className="grid gap-4 md:gap-6">
                        <div className="grid gap-2">
                            <Label htmlFor="firstName">{t('user.firstName')}</Label>
                            <Input id="firstName" placeholder={t('signUp.namePlaceholder')} {...register("firstName")}/>
                            {errors.firstName && <p className="text-red-500 text-sm">{errors.firstName.message}</p>}
                        </div>
                        <div className="grid gap-2">
                            <Label htmlFor="lastName">{t('user.lastName')}</Label>
                            <Input id="lastName" placeholder={t('signUp.lastNamePlaceholder')} {...register("lastName")}/>
                            {errors.lastName && <p className="text-red-500 text-sm">{errors.lastName.message}</p>}
                        </div>
                        <div className="grid gap-2">
                            <Label htmlFor="email">{t('user.email')}</Label>
                            <Input id="email" type="email" placeholder={t('signUp.emailPlaceholder')} {...register("email")}/>
                            {errors.email && <p className="text-red-500 text-sm">{errors.email.message}</p>}
                        </div>
                        <div className="grid gap-2">
                            <Label htmlFor="password">{t('user.password')}</Label>
                            <Input id="password" type="password" {...register("password")}/>
                            {errors.password && <p className="text-red-500 text-sm">{errors.password.message}</p>}
                        </div>
                        <div className="grid gap-2">
                            <Label htmlFor="confirmPassword">{t('signUp.confirmPassword')}</Label>
                            <Input id="confirmPassword" type="password" {...register("confirmPassword")}/>
                            {errors.confirmPassword && <p className="text-red-500 text-sm">{errors.confirmPassword.message}</p>}
                        </div>
                        <Button type="submit" className="w-full" disabled={isSubmitting}>
                            {t('signUp.submit')}
                        </Button>
                    </form>
                </CardContent>
                <CardFooter>
                    <Button variant="outline" className="w-full">
                    {t('login.googleLogin')}
                    </Button>
                </CardFooter>
            </Card>
        </div>
    );
}