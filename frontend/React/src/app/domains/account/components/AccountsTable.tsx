import { useState } from "react";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/app/domains/shared/components/table";
import { Button } from "@/app/domains/shared/components/button";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogDescription,
  DialogFooter,
  DialogClose,
} from "@/app/domains/shared/components/dialog";
import type { AccountData } from "../schemas/Account";
import { useTranslation } from "react-i18next";
import { Check, X } from "lucide-react";

type AccountTableProps = {
  accounts: AccountData[];
  onDelete?: (id: number) => void;
  onSelect?: (account: AccountData) => void;
  isAccountPage: boolean;
};

export function AccountsTable({
  accounts,
  onDelete,
  onSelect,
  isAccountPage,
}: AccountTableProps) {
  const { t } = useTranslation();
  const [selectedId, setSelectedId] = useState<string | null>(null);

  const handleSelect = (account: AccountData) => {
    setSelectedId(account.accountCode);
    onSelect?.(account);
  };

  return (
    <Table>
      <TableCaption className="table-caption">
        {t("domains.account.table.caption")}
      </TableCaption>
      <TableHeader>
        <TableRow>
          <TableHead className="table-head">
            {t("domains.account.table.headers.name")}
          </TableHead>
          <TableHead className="table-head">
            {t("domains.account.table.headers.number")}
          </TableHead>
          <TableHead className="table-head">
            {t("domains.account.table.headers.bankName")}
          </TableHead>
          <TableHead className="table-head" hidden={!isAccountPage}>
            {t("domains.account.table.headers.unlink")}
          </TableHead>
          <TableHead className="table-head" hidden={isAccountPage}>
            {t("domains.account.table.headers.select")}
          </TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {accounts.map((account) => {
          const isSelected = account.accountCode === selectedId;
          return (
            <TableRow key={account.id} className="hover:bg-transparent">
              <TableCell className="table-cell">
                <span className="span-text">
                  {account.name}
                </span>
              </TableCell>
              <TableCell className="table-cell">
                <span className="span-text">
                  {account.number}
                </span>
              </TableCell>
              <TableCell className="table-cell">
                <span className="span-text">
                  {account.bankName}
                </span>
              </TableCell>
              <TableCell className="table-cell" hidden={isAccountPage}>
                {onSelect && (
                  <Button
                    type="button"
                    variant={isSelected ? "default" : "ghost"}
                    className={`p-1 ${
                      isSelected ? "bg-green-600 text-white" : ""
                    }`}
                    onClick={() => handleSelect(account)}
                  >
                    <Check
                      className={`w-6 h-6 ${
                        isSelected ? "text-white" : "text-green-500"
                      }`}
                    />
                  </Button>
                )}
              </TableCell>
              <TableCell className="table-cell" hidden={!isAccountPage}>
                {onDelete && (
                  <Dialog>
                    <DialogTrigger asChild>
                      <Button variant="ghost" className="p-1">
                        <X className="w-6 h-6 text-red-500" />
                      </Button>
                    </DialogTrigger>
                    <DialogContent className="dialog-content">
                      <DialogHeader className="dialog-header">
                        <DialogTitle className="dialog-title">
                          {t("domains.account.table.dialog.title")}
                        </DialogTitle>
                        <DialogDescription className="dialog-description">
                          {t("domains.account.table.dialog.description", {
                            name: account.name,
                          })}
                        </DialogDescription>
                      </DialogHeader>
                      <DialogFooter className="dialog-footer">
                        <DialogClose asChild>
                          <Button
                            type="button"
                            className="button-red"
                            onClick={() => onDelete(account.id)}
                          >
                            {t("domains.account.table.dialog.confirm")}
                          </Button>
                        </DialogClose>
                        <DialogClose asChild>
                          <Button
                            type="button"
                            variant="secondary"
                            className="button-neutral"
                          >
                            {t("domains.account.table.dialog.cancel")}
                          </Button>
                        </DialogClose>
                      </DialogFooter>
                    </DialogContent>
                  </Dialog>
                )}
              </TableCell>
            </TableRow>
          );
        })}
      </TableBody>
    </Table>
  );
}
