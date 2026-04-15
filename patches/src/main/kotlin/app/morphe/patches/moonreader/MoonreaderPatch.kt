package app.morphe.patches.moonreader

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.extensions.InstructionExtensions.removeInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.moonreader.Fingerprints.CAN_SHOW_ADS
import app.morphe.patches.moonreader.Fingerprints.IS_PRO
import app.morphe.patches.moonreader.Fingerprints.LOAD_OPTIONS
import app.morphe.patches.moonreader.Fingerprints.PDF_INIT_FULL
import app.morphe.patches.moonreader.Fingerprints.PDF_IS_LICENSE_ACTIVATED
import app.morphe.patches.moonreader.Fingerprints.SET_DASH_TITLE
import app.morphe.patches.shared.Constants

@Suppress("unused")
val moonreaderProPatch =
        bytecodePatch(
                name = "Unlock Moon+ Reader Pro",
                description =
                        "Unlocks Pro features including PDF support, ad-free experience, and more.",
                default = true
        ) {
            compatibleWith(Constants.COMPATIBILITY_MOONREADER)

            execute {
                // Force A.isPro() to always return true
                IS_PRO.match(classDefBy(IS_PRO.definingClass!!)).method.apply {
                    if (implementation == null) return@apply
                    removeInstructions(0, instructions.count())
                    addInstructions(0, "const/4 v0, 0x1\nreturn v0")
                }

                // Force Global.isLicenseActivated() to return true
                PDF_IS_LICENSE_ACTIVATED.match(classDefBy(PDF_IS_LICENSE_ACTIVATED.definingClass!!))
                        .method
                        .apply {
                            if (implementation == null) return@apply
                            removeInstructions(0, instructions.count())
                            addInstructions(0, "const/4 v0, 0x1\nreturn v0")
                        }

                // Patch Global.Init(ContextWrapper, ArrayList, int, String, String, String)
                // This is the method that calls native license activation
                PDF_INIT_FULL.match(classDefBy(PDF_INIT_FULL.definingClass!!)).method.apply {
                    if (implementation == null) return@apply

                    // At the very beginning, set ms_init to true and return true
                    // This bypasses all native license checks
                    addInstructions(
                            0,
                            """
                const/4 v0, 0x1
                sput-boolean v0, Lcom/radaee/pdf/Global;->ms_init:Z
                return v0
            """.trimIndent()
                    )
                }

                // Set isProVersion and subscribeAdFree flags in LoadOptions
                LOAD_OPTIONS.match(classDefBy(LOAD_OPTIONS.definingClass!!)).method.apply {
                    if (implementation == null) return@apply

                    // Find the end of the method (last return-void)
                    val returnIndex = instructions.indexOfLast { it.opcode.name == "return-void" }
                    if (returnIndex != -1) {
                        addInstructions(
                                returnIndex,
                                """
                    const/4 v0, 0x1
                    sput-boolean v0, Lcom/flyersoft/tools/A;->isProVersion:Z
                    sput-boolean v0, Lcom/flyersoft/tools/A;->subscribeAdFree:Z
                """.trimIndent()
                        )
                    }
                }
            }
        }

@Suppress("unused")
val moonreaderUIPatch =
        bytecodePatch(
                name = "Make UI look like Pro",
                description =
                        "Changes the app title to 'Moon+ Reader Pro' and removes the 'Remove Ads' button from the drawer.",
                default = true
        ) {
            compatibleWith(Constants.COMPATIBILITY_MOONREADER)

            execute {
                // Force A.canShowAds() to always return false
                CAN_SHOW_ADS.match(classDefBy(CAN_SHOW_ADS.definingClass!!)).method.apply {
                    if (implementation == null) return@apply
                    removeInstructions(0, instructions.count())
                    addInstructions(0, "const/4 v0, 0x0\nreturn v0")
                }

                // Patch ActivityMain.setDashTitle() to use "Moon+ Reader Pro"
                SET_DASH_TITLE.match(classDefBy(SET_DASH_TITLE.definingClass!!)).method.apply {
                    if (implementation == null) return@apply

                    // Replace the entire method content to simplify logic
                    removeInstructions(0, instructions.count())
                    addInstructions(
                            0,
                            """
                iget-object v0, p0, Lcom/flyersoft/moonreader/ActivityMain;->actionbar:Landroidx/appcompat/app/ActionBar;
                invoke-virtual {p0}, Lcom/flyersoft/moonreader/ActivityMain;->recentLvVisible()Z
                move-result v1
                if-eqz v1, :cond_pro
                sget v1, Lcom/flyersoft/moonreader/R${'$'}string;->recent_list:I
                invoke-virtual {v0, v1}, Landroidx/appcompat/app/ActionBar;->setTitle(I)V
                goto :goto_next
                :cond_pro
                const-string v1, "Moon+ Reader Pro"
                invoke-virtual {v0, v1}, Landroidx/appcompat/app/ActionBar;->setTitle(Ljava/lang/CharSequence;)V
                :goto_next
                iget-object v0, p0, Lcom/flyersoft/moonreader/ActivityMain;->mmSearch:Landroid/view/MenuItem;
                if-eqz v0, :cond_end
                invoke-virtual {p0}, Lcom/flyersoft/moonreader/ActivityMain;->recentLvVisible()Z
                move-result v1
                invoke-interface {v0, v1}, Landroid/view/MenuItem;->setVisible(Z)Landroid/view/MenuItem;
                :cond_end
                return-void
            """.trimIndent()
                    )
                }
            }
        }
